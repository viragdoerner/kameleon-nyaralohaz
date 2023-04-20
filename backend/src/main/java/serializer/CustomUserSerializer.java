package serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import kameleon.dto.UserDTO;
import kameleon.model.auth.User;

import java.io.IOException;

public class CustomUserSerializer extends StdSerializer<User> {

    public CustomUserSerializer() {
        this(null);
    }

    public CustomUserSerializer(Class<User> t) {
        super(t);
    }

    @Override
    public void serialize(
            User user,
            JsonGenerator generator,
            SerializerProvider provider)
            throws IOException, JsonProcessingException {

        UserDTO dto = new UserDTO(
                user.getId(), user.getLastName(),
                user.getFirstName(), user.getEmail(),
                user.getPhonenumber(), user.hasAdminRole() );

        generator.writeObject(dto);
    }
}
