import net.rosien.landslide._

name := "generative-testing-talk"

organization := "net.rosien"

version := "1.0-SNAPSHOT"

scalaVersion := "2.9.1"

libraryDependencies ++= Seq(
  "org.scalaz" %% "scalaz-core" % "6.0.4",
  "org.specs2" %% "specs2"      % "1.12.1" % "test",
  "org.scala-tools.testing" %% "scalacheck" % "1.9" % "test"
)

site.settings

LandslideSupport.settings ++ Seq(site.addMappingsToSiteDir(mappings in LandslideSupport.Landslide, ""))

LandslideSupport.Destination := "index.html"

ghpages.settings

git.remoteRepo := "git@github.com:arosien/generative-testing-talk.git"

initialCommands := """
import scalaz._
import Scalaz._
"""

