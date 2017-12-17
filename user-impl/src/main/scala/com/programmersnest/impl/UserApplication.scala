package com.programmersnest.impl

import com.lightbend.lagom.scaladsl.broker.kafka.LagomKafkaComponents
import com.lightbend.lagom.scaladsl.server.LagomApplicationContext

abstract class UserApplication (context: LagomApplicationContext) extends UserComponents(context) with LagomKafkaComponents