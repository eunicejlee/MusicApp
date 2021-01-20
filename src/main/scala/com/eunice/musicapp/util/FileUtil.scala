package com.eunice.musicapp.util

import scala.io.BufferedSource
import scala.io.Source
import scala.collection.mutable.ArrayBuffer

object FileUtil {
               
        def getText(filename: String, sep: String = " "): ArrayBuffer[Array[String]] = {
            var openedFile : BufferedSource = null
            val rows = ArrayBuffer[Array[String]]()

            try {
                openedFile = Source.fromFile(filename)
                for (line <- openedFile.getLines) {
                    rows += line.split(",").map(_.trim)
            } 
            rows
        } finally {
            //if the file was opened, close it
            if (openedFile != null) openedFile.close()
        }
    }
  }