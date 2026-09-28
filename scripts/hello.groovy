/**
 * Minimal script confirming the classpath resolves against SNT/Fiji
 * Run this from Fiji's Script Editor (File > Open...), not via Maven
 */

import sc.fiji.snt.SNTService

println "Hello from the SNT scripting project"
println "Resolved SNTService: " + SNTService.class.name

