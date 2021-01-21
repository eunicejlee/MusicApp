package com.eunice.musicapp

import scala.util.matching.Regex
import scala.io.StdIn
import com.eunice.musicapp.model.Music
import com.eunice.musicapp.model.MusicPlaylist
import com.eunice.musicapp.dao.MusicDao
import com.eunice.musicapp.util.FileUtil
import java.io.FileNotFoundException
import scala.collection.mutable.ArrayBuffer
import java.io.IOException

class Cli {
    val commandArgPattern: Regex = "(\\w+)\\s*(.*)".r

    //prints a greeting message
    def printWelcome(): Unit = {
        val name: String = "Eunice"
        println(s"Welcome Back, $name!")
    }

    //prints the commands available to the user
    def printOptions(): Unit = {
        println("*** Discover New Music! Please Enter an Option Below.")
        println("1. Go To My Library: ")
        println("2. Search [Playlist]: [Belieber], [Jpop], [Cardio]")
        println("3. Add Music: Adds Music to Playlist")
        println("4. Delete Music: Deletes Music From Playlist")
        println("5. Update Playlist: Moves Music to Another Playlist")
        println("6. Read [filename]: Reads Text from File")
        println("7. Store [filename]: Stores to Database")
        println("*** Exit: Exits Music App CLI")
    }

    //This is the entrypoint to the Cli class
    //The menu will interact with the user on a loop and call other methods/classes
    def menu(): Unit = {
        printWelcome()
        var continueLoop = true
        while(continueLoop) {
            printOptions()
            //StdIn.readLine takes user's input
            val input = StdIn.readLine()

            input match {

                case commandArgPattern(cmd, arg) 
                if cmd.equalsIgnoreCase("go") => {
                    printAllMusic()
                }

                case commandArgPattern(cmd, arg) 
                if cmd.equalsIgnoreCase("search") => {
                    printPlaylist(arg)
                }

                case commandArgPattern(cmd,arg)
                if cmd.equalsIgnoreCase("add") && arg.equalsIgnoreCase("music") => {  
                    addPlaylist()
                }

                case commandArgPattern(cmd, arg)
                if cmd.equalsIgnoreCase("delete") && arg.equalsIgnoreCase("music") => {
                    deletePlaylist()
                }

                case commandArgPattern(cmd, arg)
                if cmd.equalsIgnoreCase("update") && arg.equalsIgnoreCase("playlist") => {
                    updatePlaylist()
                }

                case commandArgPattern(cmd, arg)
                if cmd.equalsIgnoreCase("read") => {
                    readText(arg)
                }

                case commandArgPattern(cmd, arg)
                if cmd.equalsIgnoreCase("store") => {
                    storeToTable(arg)
                }

                case commandArgPattern(cmd, arg) 
                if cmd.equalsIgnoreCase("exit") => {
                    continueLoop = false
                }

                case commandArgPattern(cmd, arg) => {
                println(s"Failed to parse command: $cmd with arguments: $arg")
                }

                case _ => {
                    println("Oops...! Option Does Not Exist!")
                }
            }
        }

        println("Come Back Soon!")
    }

    def printAllMusic(): Unit = {  
        MusicDao.getAll().foreach(println)
    }

    def printPlaylist(playlistName: String): Unit = {
        MusicDao.getPlaylist(playlistName).foreach(println)
    }

    def addPlaylist(): Unit = {
        var success : Boolean = false

        println("Music Title: ")
        val titleInput = StdIn.readLine()
        println("Playlist: ")
        val playlistInput = StdIn.readLine()
        try {
        if (MusicDao.addMusicPlaylist(MusicPlaylist(0, titleInput, playlistInput))) {
            success = true
            println("Added to Playlist")
            } else {
                success = false
                  println("Title or Playlist Not Exist")
            }
        } catch {
            case e: Exception => {
                success = false
            }
        }
    }

    def deletePlaylist(): Unit = {
        var success : Boolean = false

        println("Music Id: ")
        val musicIDInput = StdIn.readInt()
        println("Music Title: ")
        val titleInput = StdIn.readLine()
        println("Playlist: ")
        val playlistInput = StdIn.readLine()
        try {
            if (MusicDao.deleteMusic(MusicPlaylist(musicIDInput,titleInput, playlistInput))) {
                success = true
                println("Deleted from Playlist")
            } else {
                success = false
                println("Id, Title, or Playlist Not Exist")
            }
        } catch {
            case e: Exception => {
                success = false
            }
         }
    }

    def updatePlaylist(): Unit = {
        var success: Boolean = false

        println("Music Id: ")
        val musicIDInput = StdIn.readInt()
        println("Music Title: ")
        val titleInput = StdIn.readLine()
        println("Playlist: ")
        val playlistInput = StdIn.readLine()
        try {
            if (MusicDao.updateMusic(MusicPlaylist(musicIDInput,titleInput, playlistInput))) {
                success = true
                println("Playlist Updated")   
            } else {
                success = false
                println("Id, Title, or Playlist Not Exist")

            }
        } catch {
            case e: Exception => {
                success = false
            }
         }
     }

     def readText(arg: String) = {
        var musicArray = ArrayBuffer[Array[String]]()
         try {
             musicArray = FileUtil.getText(arg)
             for (rows <- musicArray) {
             println(s"${rows(0)}|${rows(1)}|${rows(2)}")
        } 
            } catch {
                case fnfe: FileNotFoundException => {
                    println(s"File Not Found: ${fnfe.getMessage}")
             }
         }
     }

     def storeToTable(arg: String) = {
         var musicArray = ArrayBuffer[Array[String]]()
         var toTable: Boolean = false

         try {
             musicArray = FileUtil.getText(arg) 
                for ( i<-0 until musicArray.length) {
                    if(MusicDao.addMusic(Music(musicArray(i)(0), musicArray(i)(1), musicArray(i)(2)))) {
                        toTable = true
                    } else {
                        toTable = false
                    }
                }
         } catch {
             case fnfe: FileNotFoundException => {
                 println(s"File Not Found: ${fnfe.getMessage}")
             }
         }
          if (toTable==true) {
                        println("Saved Successfully!")
          }
    }
}