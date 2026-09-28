#@Context context
#@SNTService snt
#@UIService ui

/**
 * file:    03_quantification_sholl.groovy
 * info:    Runs both a linear (length) and an angular (polar) Sholl profile
 *          on a curated reconstruction and plots each. This is the payoff
 *          stage: turning a trusted reconstruction into quantitative traits.
 *
 * Exercise: compare the single-value metrics (below) across two
 * different demo trees, or your own curated output from script 02.
 */


import sc.fiji.snt.SNTService
import sc.fiji.snt.Tree
import sc.fiji.snt.analysis.ShollAnalyzer
import sc.fiji.snt.analysis.sholl.gui.ShollPlot
import sc.fiji.snt.analysis.sholl.math.PolarProfileStats

// TODO (exercise): swap in the reconstruction you curated in script 02
Tree tree = snt.demoTree("DG")
def analyzer = new ShollAnalyzer(tree)
((SNTService) snt).assignValues(false)
println "Single-value Sholl metrics:"
println analyzer.getSingleValueMetrics()

def linearStats = analyzer.getLinearStats()
new ShollPlot(linearStats).show()

def polarStats = new PolarProfileStats(linearStats, 10.0d)   // 10-degree angular bins
polarStats.getPlot().show()
// Documentation: https://imagej.net/plugins/snt/scripting
