package com.programmersnest.impl.repository

import akka.Done
import com.lightbend.lagom.scaladsl.api.transport.{ExceptionMessage, NotAcceptable, TransportErrorCode}
import com.lightbend.lagom.scaladsl.persistence.jdbc.JdbcSession
import com.programmersnest.api.models.User

import scala.concurrent.Future

/**
  * Created by harmeet on 6/5/17.
  */
class UserRepository(session: JdbcSession) {

  import JdbcSession.tryWith

  def addNewUser(user: User): Future[Done] = {
    val sql =
      """
        |INSERT INTO users (id, date, name)
        |VALUES
        |(?, ?, ?);
      """.stripMargin

    session.withConnection(con => {
      tryWith(con.prepareStatement(sql)) { statement => {
        statement.setString(1, user.id.get)
        statement.setTimestamp(2, user.date.get)
        statement.setString(3, user.name)
        if(!statement.execute()) Done else throw
          new NotAcceptable(TransportErrorCode.UnsupportedData, new ExceptionMessage("Invalid Data", "User properties invalid"))
      }}
    })
  }
}
