package com.eunice.musicapp

import com.eunice.musicapp.dao.MusicDao
import com.eunice.musicapp.model.Music
import com.eunice.musicapp.model.MusicPlaylist

object Driver {
    def main(args: Array[String]): Unit = {
        val cli = new Cli()
        cli.menu()
    }
}