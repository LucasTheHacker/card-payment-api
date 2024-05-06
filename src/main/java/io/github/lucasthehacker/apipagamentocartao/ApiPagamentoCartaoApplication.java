package io.github.lucasthehacker.apipagamentocartao;

import jakarta.ws.rs.core.Application;
import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Contact;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.info.License;

@OpenAPIDefinition(
        info = @Info(
                title="api-pagamento-cartao",
                version = "1.0",
                contact = @Contact(
                        name = "Lucas Batista",
                        url = "https://github.com/LucasTheHacker/api-pagamento-cartao",
                        email = "lucas.batista@bb.com.br"),
                license = @License(
                        name = "Apache Maven 3.9.5",
                        url = "https://www.apache.org/licenses/LICENSE-2.0.html"))
)
public class ApiPagamentoCartaoApplication extends Application {
}
