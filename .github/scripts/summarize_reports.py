#!/usr/bin/env python3
"""Summarize JUnit test results and Jacoco coverage into a Markdown comment for the PR.

Run from the repo root after `./gradlew testDebugUnitTest checkCoverage aggregateCoverage`
(with --continue, so reports exist even if a task failed). Exits 0 always: this script only
*reports*, it never decides pass/fail -- the calling CI job's own exit code (from the Gradle
step) is what actually blocks the merge.
"""
import glob
import xml.etree.ElementTree as ET

MARKER = "<!-- ci-summary -->"
COVERAGE_THRESHOLD = 80.0


def parse_test_results():
    total = failures = errors = skipped = 0
    failed_tests = []
    for path in sorted(glob.glob("**/build/test-results/testDebugUnitTest/TEST-*.xml", recursive=True)):
        try:
            root = ET.parse(path).getroot()
        except ET.ParseError:
            continue
        total += int(root.get("tests", 0))
        failures += int(root.get("failures", 0))
        errors += int(root.get("errors", 0))
        skipped += int(root.get("skipped", 0))
        for testcase in root.findall("testcase"):
            failure = testcase.find("failure")
            error = testcase.find("error")
            node = failure if failure is not None else error
            if node is not None:
                classname = testcase.get("classname", "")
                name = testcase.get("name", "")
                message = (node.get("message") or "").splitlines()[0] if node.get("message") else ""
                failed_tests.append(f"`{classname}.{name}` — {message}".rstrip(" —"))
    return total, failures, errors, skipped, failed_tests


def counters(report_root):
    result = {}
    for counter in report_root.findall("counter"):
        t = counter.get("type")
        missed = int(counter.get("missed"))
        covered = int(counter.get("covered"))
        result[t] = (covered, missed)
    return result


def pct(covered, missed):
    total = covered + missed
    return (covered / total * 100.0) if total else None


def parse_module_coverage():
    rows = []
    for path in sorted(glob.glob("*/build/jacoco/reports/jacocoAndroidTestReport/jacocoAndroidTestReport.xml")):
        module = path.split("/")[0]
        try:
            root = ET.parse(path).getroot()
        except ET.ParseError:
            continue
        c = counters(root)
        if "INSTRUCTION" not in c:
            continue
        instr = pct(*c["INSTRUCTION"])
        rows.append((module, instr))
    return rows


def parse_aggregate_coverage():
    try:
        root = ET.parse("build/jacoco/reports/aggregateCoverage/aggregateCoverage.xml").getroot()
    except (ET.ParseError, FileNotFoundError):
        return None
    return counters(root)


def main():
    total, failures, errors, skipped, failed_tests = parse_test_results()
    passed = total - failures - errors - skipped

    lines = [MARKER, "## CI results", ""]

    if total == 0:
        lines.append("No test results found.")
    else:
        status = "✅" if failures == 0 and errors == 0 else "❌"
        lines.append(f"### {status} Unit tests: {passed}/{total} passed")
        if failed_tests:
            lines.append("")
            lines.append("<details><summary>Failed tests</summary>\n")
            for t in failed_tests:
                lines.append(f"- {t}")
            lines.append("\n</details>")

    lines.append("")

    agg = parse_aggregate_coverage()
    module_rows = parse_module_coverage()
    below = [(m, p) for m, p in module_rows if p is not None and p < COVERAGE_THRESHOLD]
    cov_status = "✅" if not below else "❌"
    lines.append(f"### {cov_status} Coverage (min {COVERAGE_THRESHOLD:.0f}% per module)")
    lines.append("")
    if agg:
        agg_instr = pct(*agg["INSTRUCTION"]) if "INSTRUCTION" in agg else None
        agg_line = pct(*agg["LINE"]) if "LINE" in agg else None
        if agg_instr is not None:
            lines.append(f"Overall: **{agg_instr:.1f}%** instructions, **{agg_line:.1f}%** lines")
            lines.append("")
    if module_rows:
        lines.append("| Module | Instructions |")
        lines.append("|---|---|")
        for module, p in module_rows:
            mark = " ⚠️" if p is not None and p < COVERAGE_THRESHOLD else ""
            value = f"{p:.1f}%{mark}" if p is not None else "n/a"
            lines.append(f"| {module} | {value} |")
    else:
        lines.append("No coverage reports found.")

    print("\n".join(lines))


if __name__ == "__main__":
    main()
