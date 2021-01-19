package com.eunice.musicapp.model

import java.sql.ResultSet

case class Music (
    musicTitle: String,
    artistName: String,
    genreType: String
) {}

object Music {
    def fromResultSet(rs: ResultSet) = {
        apply (
            rs.getString("music_title"),
            rs.getString("artist_name"),
            rs.getString("genre_type")
        )
    }
}