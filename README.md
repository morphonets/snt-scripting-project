# SNT scripting project

A minimal Maven project to help with SNT/Fiji Groovy scripting.

## What's this for?

Fiji's Script Editor is functional but has no real autocompletion for Groovy. IDEs
on the other hand, offer autocomplete, jump-to-source, and inline SNT/ImageJ Javadoc,
which makes writing anything beyond a trivial script much easier.

This project (inspired by QuPath's [gradle-scripting-project](https://github.com/qupath/qupath-gradle-scripting-project))
sets this up automatically: It solves the tricky part of getting the IDE to see SNT
and all of Fiji's other libraries.

## How do I use it?

### Scripting with IntelliJ

* Download or clone this repo
* In IntelliJ (ultimate subscription is **NOT** needed): *File -> Open...* and select this
  folder: IntelliJ will detect the `pom.xml` and import it as a Maven project automatically
* Let it finish indexing (first import downloads SNT + dependencies; note it can take several
  minutes)
* IntelliJ should also pop up a notification that it found `snt-scijava-params.gdsl` - accept/enable
  it (or go to *Settings -> Languages & Frameworks -> Groovy -> Groovy DSL Awareness*). This teaches
  IntelliJ about Scijava @ parameters. It won't clear the squiggles on the `#@...` header lines
  themselves (see note below)
* Write scripts as `.groovy` files inside `scripts/`
* Enjoy real autocompletion, navigation, and inline docs against SNT/ImageJ
* Whenever you want to actually run a script, open it in Fiji's own Script Editor (or drag the file
  onto Fiji's main window). This project is for _editing_ scripts. It does not run them standalone.

### Scripting with VS Code

Integration with VS Code is more fragile than the IntelliJ route, but still possible:

1. Run `mvn dependency:resolve` _once_ in this folder so all jars are cached to `~/.m2/repository`
2. Install a Groovy language server build and a generic LSP-client extension (e.g. `glspc`)
3. Point `groovy.classpath` in VS Code settings at the resolved jars, e.g.
   the output of `mvn dependency:build-classpath -Dmdep.outputFile=cp.txt`


### Starter scripts

`scripts/` ships four files:

* `hello.groovy`: sanity check that the everything is properly setup
* `01_precursor_autotrace.groovy`: auto-traces the ddaC demo image
* `02_curation_quality_report.groovy`: runs plausibility/crossover checks
* `03_quantification_sholl.groovy`: linear + angular Sholl profiles

Each has a `// TODO (exercise)` marking where to adapt it to your own data

> **Note on the `#@` header lines**
>
> Lines like `#@SNTService snt` are SciJava's [parameter-injection syntax](https://imagej.net/scripting/parameters).
> Fiji's Script Editor strips and interprets them before compiling the rest
> of the script - that's how `snt`, `ui`, etc. get bound automatically. Plain
> IDEs don't know this convention, so IntelliJ/VS Code may underline those
> lines as a syntax error. That's cosmetic: the scripts still run correctly
> when opened in Fiji's own Script Editor, which is where you actually run them

## Troubleshooting

### If the GDSL descriptor doesn't take effect

`snt-scijava-params.gdsl` (see above) is what gives `snt`, `ui`, `context`,
etc. real types. If IntelliJ never picks it up (no per-file GDSL settings
page exists in recent IntelliJ versions, and the one-time "trust this
script" notification is easy to miss/dismiss with no obvious way back), it's
not worth losing time over: everything else still autocompletes normally,
since actual SNT/ImageJ classes resolve straight from the Maven dependency.

The only casualty is the handful of injected names themselves showing as
"unresolved symbols". A one-line workaround, whenever you want autocomplete
past one of them, is an inline cast:

```groovy
((SNTService) snt).getPathAndFillManager()   // autocompletes after the cast
((UIService) ui).showDialog("done")
```

This doesn't change runtime behavior (Fiji still injects the real object;
the cast is a no-op at runtime beyond a type-check) - it's purely to give
IntelliJ enough information to suggest members past that point.

### Keeping the SNT version in sync

Bump `<snt.version>` in `pom.xml` to match whatever SNT release (or
`-SNAPSHOT`) you're targeting, then re-import the Maven project (IntelliJ:
the little Maven refresh icon, or *Reload All Maven Projects*).

## License
Just a template, use as you wish, no warranty.
