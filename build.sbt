enablePlugins(ScalaJSPlugin)

name := "ScalaJSfun"
scalaVersion := "2.13.1"

// This is an application with a main method
scalaJSUseMainModuleInitializer := true
mainClass := Some("scalajs.fun.Main")

libraryDependencies += "org.scala-js" %%% "scalajs-dom" % "1.0.0"

scalaJSLinkerConfig ~= { _.withESFeatures(_.withUseECMAScript2015(false)) }
