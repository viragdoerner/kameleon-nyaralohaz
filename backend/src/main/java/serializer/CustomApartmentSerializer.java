package serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import kameleon.dto.ApartmentDTO;
import kameleon.model.apartman.Apartment;

import java.io.IOException;

public class CustomApartmentSerializer extends StdSerializer<Apartment> {

    public CustomApartmentSerializer() {
        this(null);
    }

    public CustomApartmentSerializer(Class<Apartment> t) {
        super(t);
    }

    @Override
    public void serialize(
            Apartment apartment,
            JsonGenerator generator,
            SerializerProvider provider)
            throws IOException, JsonProcessingException {

        ApartmentDTO dto = new ApartmentDTO(
                apartment.getId(), apartment.getName(),
                apartment.getDescription(), apartment.getPrice());

        generator.writeObject(dto);
    }
}
