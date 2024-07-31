package com.monjane.beprepared.swagger;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@OpenAPIDefinition(
        info = @Info(
                title = "BePrepared",
                description = "A BePrepared é uma solução inovadora desenvolvida para " +
                        "minimizar o impacto de desastres naturais em Moçambique. " +
                        "Esta UI, criada por Lourenço Monjane tem como objetivo facilitar" +
                        " a comunicação de riscos climáticos e aumentar a preparação da " +
                        "população local.",
                version = "1.0",
                contact = @Contact(
                        name = "Lourenço Monjane",
                        email = "louramonja52.n@gmail.com",
                        url = "https://github.com/monjane10/"
                ),
                license = @License(
                        name = "Apache License 2.0",
                        url = "https://www.apache.org/licenses/LICENSE-2.0"
                )

        ),
        security = {
                @SecurityRequirement(
                        name = "Bearer Authentication"
                )
        }
)

@SecurityScheme(
        name = "Bearer Authentication",
        description = "Faça o login na API, para poder usar perfeitamente a aplicação BePrepared," +
                "coloque o seu token de autenticação no campo abaixo  e clique no botão authorize.",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
public class OpenApiConfig {
}