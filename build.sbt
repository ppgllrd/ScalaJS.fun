enablePlugins(ScalaJSPlugin)

name := "ScalaJSFun"
scalaVersion := "3.4.1"

// This is an application with a main method
scalaJSUseMainModuleInitializer := true

libraryDependencies += "org.scala-js" %%% "scalajs-dom" % "2.8.0"

// Add support for the DOM in `run` and `test`
jsEnv := new org.scalajs.jsenv.jsdomnodejs.JSDOMNodeJSEnv()
