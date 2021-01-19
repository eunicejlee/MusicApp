package com.eunice.musicapp.model

import java.sql.ResultSet

case class Playlist (
    playlistName: String
) {}

object Playlist {
    def fromResultSet(rs: ResultSet) = {
        apply (
            rs.getString("playlist_name")
        )
    }

}