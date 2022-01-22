package in.mcxiv.jpsd.io;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class IOTest {

    @Test
    void generalTest() throws IOException {

        String str = "This is some data, okay?";
        System.out.println(str);

        DataWriter writer = new DataWriter();
        writer.writeString(str);
        for (byte b : writer.toByteArray()) System.out.print((char)b); System.out.println();
        writer.writePascalStringRaw(str);
        for (byte b : writer.toByteArray()) System.out.print((char)b); System.out.println();
        writer.writePascalStringPaddedTo4(str);
        for (byte b : writer.toByteArray()) System.out.print((char)b); System.out.println();
        writer.writePascalStringEvenlyPadded(str);
        for (byte b : writer.toByteArray()) System.out.print((char)b); System.out.println();
        writer.writeUnicodeString(str);

        for (byte b : writer.toByteArray()) System.out.print((char)b);

        System.out.println("\n\nR\n");

        DataReader reader = new DataReader(writer.toByteArray());

        assertAndPrint(str, reader.readString(str.length()));
        assertAndPrint(str, reader.readPascalStringRaw());
        assertAndPrint(str, reader.readPascalStringPaddedTo4());
        assertAndPrint(str, reader.readPascalStringEvenlyPadded());
        assertAndPrint(str, reader.readUnicodeString());

    }

    public void assertAndPrint(String ex, String act) {
        System.out.println(act);
        Assertions.assertEquals(ex, act);
    }

}
