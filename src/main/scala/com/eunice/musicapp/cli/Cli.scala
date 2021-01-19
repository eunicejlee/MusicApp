package com.eunice.musicapp

import scala.util.matching.Regex
import scala.io.StdIn
import com.eunice.musicapp.model.Music
import com.eunice.musicapp.model.MusicPlaylist
import com.eunice.musicapp.dao.MusicDao
import com.eunice.musicapp.util.FileUtil
import java.io.FileNotFoundException

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
        println("1. My Library: ")
        println("2. Search [Playlist]:  ")
        println("3. Add Music: Adds Music to Playlist")
        println("4. Delete Music: Deletes Music From Playlist")
        println("5. Update Playlist: Moves Music to Another Playlist")
        println("6. Find [filename]: Reads Text from File")
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
                if cmd.equalsIgnoreCase("library") => {
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
                if cmd.equalsIgnoreCase("find") => {
                    printText(arg)
                }

                case commandArgPattern(cmd, arg) 
                if cmd.equalsIgnoreCase("exit") => {
                    continueLoop = false
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
        println("Music Title: ")
        val titleInput = StdIn.readLine()
        println("Playlist: ")
        val playlistInput = StdIn.readLine()
        try {
        if (MusicDao.addMusic(MusicPlaylist(0, titleInput, playlistInput))) {
            println("Added to Playlist")
            } 
        } catch {
            case e: Exception => {
                println("Something Went Wrong")
        }
    }
}

    def deletePlaylist(): Unit = {
        println("Music Id: ")
        val musicIDInput = StdIn.readInt()
        println("Music Title: ")
        val titleInput = StdIn.readLine()
        println("Playlist: ")
        val playlistInput = StdIn.readLine()
        try {
            if (MusicDao.updateMusic(MusicPlaylist(musicIDInput,titleInput, playlistInput))) {
                println("Deleted from Playlist")
            }
        } catch {
            case e: Exception => {
                println("Something Went Wrong")
            }
         }
    }

    def updatePlaylist(): Unit = {
        println("Music Id: ")
        val musicIDInput = StdIn.readInt()
        println("Music Title: ")
        val titleInput = StdIn.readLine()
        println("Playlist: ")
        val playlistInput = StdIn.readLine()
        try {
            if (MusicDao.updateMusic(MusicPlaylist(musicIDInput,titleInput, playlistInput))) {
                println("Playlist Updated")
            }
        } catch {
            case e: Exception => {
                println("Something Went Wrong")
            }
         }
     }

     def printText(arg: String) = {
         try {
             println(FileUtil.getText(arg))
         } catch {
             case fnfe: FileNotFoundException => {
                 println(s"File Not Found: ${fnfe.getMessage}")
             }
         }
     }
}