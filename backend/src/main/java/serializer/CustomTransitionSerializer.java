package serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import kameleon.dto.ApartmentDTO;
import kameleon.dto.StatusTransitionDTO;
import kameleon.model.apartman.Apartment;
import kameleon.model.booking.StatusTransition;

import java.io.IOException;

public class CustomTransitionSerializer extends StdSerializer<StatusTransition> {

    public CustomTransitionSerializer() {
        this(null);
    }

    public CustomTransitionSerializer(Class<StatusTransition> t) {
        super(t);
    }

    @Override
    public void serialize(
            StatusTransition transition,
            JsonGenerator generator,
            SerializerProvider provider)
            throws IOException, JsonProcessingException {

        StatusTransitionDTO dto = new StatusTransitionDTO(transition);

        generator.writeObject(dto);
    }
}

