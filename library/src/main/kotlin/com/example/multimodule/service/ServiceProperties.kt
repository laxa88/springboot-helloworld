package com.example.multimodule.service

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("myconfig")
data class ServiceProperties(
    var myMessage: String = ""
)
