#@Context context
#@SNTService snt
#@UIService ui

/**
 * file:    02_curation_quality_report.groovy
 * info:    Runs SNT's plausibility/quality checks (including crossover
 *          detection) on a reconstruction and prints the findings. This is
 *          how you validate a precursor before trusting it as ground truth.
 *
 * Exercise: run this on the tree produced by
 * 01_precursor_autotrace.groovy (swap demoTree(...) for whatever Tree you
 * just traced), then fix the flagged issues in the Path Manager and re-run
 * to confirm the warnings clear.
 */

import sc.fiji.snt.Tree
import sc.fiji.snt.analysis.curation.PlausibilityMonitor

// TODO (exercise): point this at your own reconstruction instead of the demo
Tree tree = snt.demoTree("DG")

def monitor = new PlausibilityMonitor()
def warnings = monitor.runFullScan(tree.list())

if (warnings.isEmpty()) {
    println "No issues found - this reconstruction passes all plausibility checks"
} else {
    warnings.each { w ->
        println "[${w.severity()}] ${w.checkName()}: ${w.message()} (impact=${w.impact()})"
    }
}
// Documentation: https://imagej.net/plugins/snt/scripting
