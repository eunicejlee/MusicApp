package com.eunice.musicapp.model

import java.sql.ResultSet

case class Artist (
    artistName: String
) {}

object Artist {
    def fromResultSet(rs: ResultSet) = {
        apply (
            rs.getString("artist_name")
        )
    }

}