package com.eunice.musicapp.model

import java.sql.ResultSet

case class MusicPlaylist (
    mpId: Int,
    musicTitle: String,
    playlistName: String
) {}

object MusicPlaylist {
    def fromResultSet(rs: ResultSet) = {
        apply (
        rs.getInt("mp_id"),
        rs.getString("music_title"),
        rs.getString("playlist_name")
        )
    }

}