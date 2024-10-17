ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.3.1"

lazy val root = (project in file("."))
  .settings(
    name := "Scothello",
    scalaVersion := "3.3.0",
    libraryDependencies ++= Seq(
      "org.scalatest" %% "scalatest" % "3.3.0-SNAP4" % Test,
      "org.scalafx" %% "scalafx" % "20.0.0-R31",
      "org.scalatestplus" %% "scalacheck-1-17" % "3.2.18.0" % Test,
    ),
    libraryDependencies ++= {
      // Determine OS version of JavaFX binaries
      lazy val osName = System.getProperty("os.name") match {
        case n if n.startsWith("Linux")   => "linux"
        case n if n.startsWith("Mac")     => "mac"
        case n if n.startsWith("Windows") => "win"
        case _                            => throw new Exception("Unknown platform!")
      }
      Seq("base", "controls", "fxml", "graphics", "media", "swing", "web")
        .map(m => "org.openjfx" % s"javafx-$m" % "16" classifier osName)
    }
  )
