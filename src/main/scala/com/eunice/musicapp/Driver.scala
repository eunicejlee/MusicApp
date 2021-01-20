package com.eunice.musicapp

import com.eunice.musicapp.dao.MusicDao
import com.eunice.musicapp.model.Music
import com.eunice.musicapp.model.MusicPlaylist

object Driver {
    def main(args: Array[String]): Unit = {
        val cli = new Cli()
        cli.menu()
        //println(MusicDao.getPlaylist("Belieber"))
        //MusicDao.addMusicPlaylist(MusicPlaylist(0, "Nothing", "Belieber"))
        //println(MusicDao.getPlaylist("Belieber"))
        //MusicDao.deleteMusic(MusicPlaylist(10, "Anyone", "Belieber"))
        //MusicDao.updateMusic(MusicPlaylist(12, "Come Around Me", "Chil Beats"))
        //println(MusicDao.getPlaylist("Belieber"))
        
    }
}