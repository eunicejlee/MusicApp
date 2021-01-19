package com.eunice.musicapp.util

import scala.io.BufferedSource
import scala.io.Source

object FileUtil {
    def getText(filename: String, sep: String = " "): String = {
        var openedFile: BufferedSource = null

        try {
            println("Music Title, Artist Name, Genre")
            openedFile = Source.fromFile(filename)
            openedFile.getLines().mkString(sep)
            // for (line <- openedFile.getLines.mkString(sep)) {
            //     val cols = line.split(",").map(_.trim)
            //}
        } finally {
            if (openedFile != null) openedFile.close()
        }
    }
}