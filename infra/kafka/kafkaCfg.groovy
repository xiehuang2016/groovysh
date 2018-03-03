settings {
    ka.hosts = ["xly04", "xly05"] as List
    zk.hosts = ["xly01", "xly02", "xly03"] as List
    zk.client.port = 12181
    ka.client.port = "9092"
}

config {
    settings.ka.hosts.eachWithIndex { host, idx ->
        "server-(${host}).properties" {
            listeners = "PLAINTEXT://:${settings.ka.client.port}"
            log.dirs = "/data0/kafka/data"
            num.partitions = 2
            broker.id = settings.ka.hosts.indexOf(host) + 1
            zookeeper.connect = settings.zk.hosts.collect { "${it}:${settings.zk.client.port}" }.join(",") + "/kafka"
        }

    }

    'producer.properties' {
        bootstrap.servers = settings.ka.hosts.collect { "${it}:${settings.ka.client.port}" }.join(",")
    }

    'consumer.properties' {
        bootstrap.servers = settings.ka.hosts.collect { "${it}:${settings.ka.client.port}" }.join(",")
    }

    'log4j.properties' {
        kafka.logs.dir = "/data0/kafka/log"
        log4j.appender.kafkaAppender.MaxBackupIndex = 3
        log4j.appender.kafkaAppender.MaxFileSize = "10MB"
        log4j.appender.stateChangeAppender.MaxBackupIndex = 3
        log4j.appender.stateChangeAppender.MaxFileSize = "10MB"
        log4j.appender.requestAppender.MaxBackupIndex = 3
        log4j.appender.requestAppender.MaxFileSize = "10MB"
        log4j.appender.cleanerAppender.MaxBackupIndex = 3
        log4j.appender.cleanerAppender.MaxFileSize = "10MB"
        log4j.appender.controllerAppender.MaxBackupIndex = 3
        log4j.appender.controllerAppender.MaxFileSize = "10MB"
        log4j.appender.authorizerAppender.MaxBackupIndex = 3
        log4j.appender.authorizerAppender.MaxFileSize = "10MB"
    }
}



