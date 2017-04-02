import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Binarizer
{
    private long phrase_count = 1;
    private boolean is_first = true;
    private static BufferedWriter bw;

    private void writeInBinary( long value, int num_bits ) throws IOException
    {
        assert( num_bits != 0 && num_bits <= 8 );
        for( int i = num_bits - 1; i >= 0; i-- )
        {
            long bit = ( value >> i ) & 1;
            bw.write( ( bit == 1 ) ? "1" : "0" );
        }
    }

    public void binarize( long phrase_num, char prefix, boolean has_mismatch ) throws IOException
    {
        int num_bits = Long.SIZE - Long.numberOfLeadingZeros( phrase_count );

        writeInBinary( phrase_num, num_bits );

        if( has_mismatch )
        {
            writeInBinary( prefix, 8 );
            phrase_count++;
        }

        if( phrase_num == 0 )
        {
            phrase_count = 1;
        }

        is_first = false;
    }

    public static void main( String[] args ) throws IOException
    {
        final BufferedReader br = new BufferedReader( new InputStreamReader( System.in, "UTF-8" ) );
        bw = new BufferedWriter( new OutputStreamWriter( System.out, "UTF-8" ) );
        Binarizer binarizer = new Binarizer();

        for( String line = br.readLine(); line != null; line = br.readLine() )
        {
            String[] tokens = line.split( ",", 2 ); // Limit to 2 for case where value is ','
            long phrase_num = Long.parseLong( tokens[0] );
            char prefix = tokens.length == 2 ? (char) Integer.parseInt( tokens[1], 16 ) : '\0' /* can be anything */;

            binarizer.binarize( phrase_num, prefix, tokens.length == 2 );
        }

        br.close();
        bw.close();
    }
}
