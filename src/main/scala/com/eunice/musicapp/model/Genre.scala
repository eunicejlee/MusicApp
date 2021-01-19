package com.eunice.musicapp.model

import java.sql.ResultSet

case class Genre (
    genreType: String
) {}

object Genre {
    def fromResultSet(rs: ResultSet) = {
        apply (
            rs.getString("gebre_type")
        )
    }

}