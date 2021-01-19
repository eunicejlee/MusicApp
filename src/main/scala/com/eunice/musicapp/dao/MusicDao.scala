package com.eunice.musicapp.dao

import scala.collection.mutable.ArrayBuffer
import com.eunice.musicapp.model.Music
import com.eunice.musicapp.model.MusicPlaylist
import com.eunice.musicapp.util.ConnectionUtil
import scala.util.Using

/**
  * Music Data Access Object
  * CRUD: CREATE, READ, UPDATE, DELETE Method 
  */

  object MusicDao {

    /**
      * READ
      *
      * @return all books
      *      
      */
    def getAll(): Seq[Music] = {
      val conn = ConnectionUtil.getConnection()
      Using.Manager { use =>
      val stmt = use(conn.prepareStatement("SELECT * FROM music;"))
      stmt.execute()
      val rs = use(stmt.getResultSet())
      val allMusic: ArrayBuffer[Music] = ArrayBuffer()
      while(rs.next()) {
        allMusic.addOne(Music.fromResultSet(rs))
        }
        allMusic.toList
      }.get
    }

    def getPlaylist(playlistName: String): Seq[MusicPlaylist] = {
      val conn = ConnectionUtil.getConnection()
      Using.Manager { use =>
      val stmt = use(conn.prepareStatement("SELECT * FROM music_playlist WHERE playlist_name = ?;"))
      stmt.setString(1, playlistName)
      stmt.execute()
      val rs = use(stmt.getResultSet())
      val playlistMusic: ArrayBuffer[MusicPlaylist] = ArrayBuffer()
      while(rs.next()) {
        playlistMusic.addOne(MusicPlaylist.fromResultSet(rs))
      }
      playlistMusic.toList
     } .get
    }

      /**
        * CREATE
        *
        * @param musicPlaylist
        * @return add music to a playlist
        */
      def addMusic(musicPlaylist: MusicPlaylist): Boolean = {
          val conn = ConnectionUtil.getConnection()
          Using.Manager { use =>
            val stmt = use(conn.prepareStatement
            ("INSERT INTO music_playlist VALUES (DEFAULT,?, ?);"))
            stmt.setString(1, musicPlaylist.musicTitle)
            stmt.setString(2, musicPlaylist.playlistName)
            stmt.execute()
            stmt.getUpdateCount() > 0
        } .getOrElse(false)
      }

      /**
        * UPDATE
        *
        * @param musicPlaylist
        * @return moves music in the playlist to a different playlist
        */
      def updateMusic (musicPlaylist: MusicPlaylist): Boolean = {
        val conn = ConnectionUtil.getConnection()
        Using.Manager { use =>
          val stmt = use(conn.prepareStatement
          ("UPDATE music_playlist SET playlist_name = ? WHERE mp_id = ? AND music_title = ?"))
          stmt.setString(1, musicPlaylist.playlistName)
          stmt.setInt(2, musicPlaylist.mpId)
          stmt.setString(3, musicPlaylist.musicTitle)
          stmt.execute()
          stmt.getUpdateCount() > 0
        } .getOrElse(false)
      }

      /**
        * DELETE
        *
        * @param playlist
        * @return deltes music from a playlist
        */
      def deleteMusic(musicPlaylist: MusicPlaylist): Boolean = {
          val conn = ConnectionUtil.getConnection()
          Using.Manager { use =>
            val stmt = use(conn.prepareStatement
            ("DELETE FROM music_playlist WHERE music_id = ?"))
            stmt.setInt(1, musicPlaylist.mpId)
            stmt.execute()
            stmt.getUpdateCount() > 0
        } .getOrElse(false)
      }
    }
