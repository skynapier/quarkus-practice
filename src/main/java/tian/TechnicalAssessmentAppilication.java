package tian;


import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Contact;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("/")
@OpenAPIDefinition(
        info = @Info(title = "Technical Assessment API",
                description = "This API allows CRUD operations on technical assessment records",
                version = "1.0",
                contact = @Contact(name = "@tian.bai")),
        tags = {
                @Tag(name = "api", description = "Public API that can be used by anybody"),
                @Tag(name = "records", description = "Entity of the Technical Assessment")
        }
)
public class TechnicalAssessmentAppilication extends Application {
}
