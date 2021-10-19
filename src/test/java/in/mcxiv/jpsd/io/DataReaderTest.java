package in.mcxiv.jpsd.io;

import org.junit.jupiter.api.Test;

import javax.imageio.stream.ImageInputStreamImpl;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

import static org.junit.jupiter.api.Assertions.*;

class DataReaderTest {

    @Test
    void name() throws IOException {

        ByteArrayInputStream stream = new ByteArrayInputStream(ByteBuffer.allocate(Double.BYTES).putDouble(
                Double.longBitsToDouble(0b0_000_0010__0000_0000__0000_0000__0000_0000L)).array());

        DataReader reader = new DataReader(new ImageInputStreamImpl() {

            @Override
            public int read() throws IOException {
                return stream.read();
            }

            @Override
            public int read(byte[] b, int off, int len) throws IOException {
                return stream.read(b, off, len);
            }
        });

        double v = reader.readFDouble();

        System.out.println(v);

        assertEquals(2, v);

    }
}