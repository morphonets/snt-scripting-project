#@Context context
#@SNTService snt
#@UIService ui

/**
 * file:    01_precursor_autotrace.groovy
 * info:    Loads the ddaC demo image and produces a first-pass, unvalidated
 *          tracing using auto-tracing (BinaryTracer). This is the "precursor"
 *          stage of the SNT5 pipeline: fast, but not yet trustworthy.
 *
 * Exercise: after running this as-is, try switching DEMO_NAME to
 * "microglia" and adapt the tracer setup to a multi-soma image instead
 * (see SNT's bundled demo_microglia_multisoma.groovy for the pattern).
 */

import sc.fiji.snt.tracing.auto.BinaryTracer

// TODO (exercise): try "microglia" instead - needs GWDTTracerFactory + SomaUtils,
// see demo_microglia_multisoma.groovy bundled with SNT for the multi-soma pattern
def DEMO_NAME = "ddaC"

def sntInstance = snt.initialize("demo:${DEMO_NAME}", true)
def imp = sntInstance.getImagePlus()
def dup = imp.duplicate()

def tracer = new BinaryTracer(dup, true)               // true = skeletonize first
tracer.setPruneMode(BinaryTracer.PERIPHERAL_SEGMENTS)
tracer.setRootRoi(imp.getRoi(), BinaryTracer.ROI_EDGE)
tracer.setPruneByLength(true)
tracer.setLengthThreshold(3)                            // microns; TODO: tweak and observe effect

def trees = tracer.getTrees()
sntInstance.getPathAndFillManager().addTrees(trees, "BinaryTracer")
sntInstance.updateAllViewers()

println "Precursor reconstruction done: ${trees.size()} tree(s) added to Path Manager"
// Documentation: https://imagej.net/plugins/snt/scripting
