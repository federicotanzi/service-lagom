package com.programmersnest.impl

import com.lightbend.lagom.scaladsl.persistence.jdbc.JdbcPersistenceComponents
import com.lightbend.lagom.scaladsl.server.{LagomApplication, LagomApplicationContext, LagomServer}
import com.programmersnest.api.UserService
import com.programmersnest.impl.repository.UserRepository
import com.softwaremill.macwire.wire
import play.api.db.HikariCPComponents
import play.api.libs.ws.ahc.AhcWSComponents

abstract class UserComponents(context: LagomApplicationContext) extends LagomApplication(context)
  with JdbcPersistenceComponents with HikariCPComponents with AhcWSComponents {

  override lazy val jsonSerializerRegistry = UserJsonSerializerRegistry

  override def lagomServer: LagomServer = serverFor[UserService](wire[UserServiceImpl])

  persistentEntityRegistry.register(wire[UserEntity])
  lazy val userRepository = wire[UserRepository]
  readSide.register(wire[UserEventProcessor])
}

