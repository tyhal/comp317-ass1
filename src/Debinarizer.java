import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Debinarizer
{
    private long phrase_count = 1;
    private long bits_to_decode = 0;
    private long bit_string = 0;
    private long bit_count = 0;
    private boolean decode_prefix = false;
    private static BufferedWriter bw;

    public void debinarize( int bit ) throws IOException
    {
        bits_to_decode = decode_prefix ? 8 : Long.SIZE - Long.numberOfLeadingZeros( phrase_count );

        bit_string ^= bit;
        bit_count++;

        if( bit_count == bits_to_decode )
        {
            if( decode_prefix )
            {
                bw.write( "," + Integer.toHexString( (int)bit_string ) + "\n" );
                phrase_count++;
            }
            else
            {
                bw.write( Long.toString( bit_string ) );

                if( bit_string == 0 )
                {
                    phrase_count = 0;
                }
            }

            decode_prefix = !decode_prefix; // Alternate
            bit_count = 0;
            bit_string = 0;
        }
        bit_string <<= 1;
    }

    public static void main( String[] args ) throws IOException
    {
        final BufferedReader br = new BufferedReader( new InputStreamReader( System.in, "UTF-8" ) );
        bw = new BufferedWriter( new OutputStreamWriter( System.out, "UTF-8" ) );
        Debinarizer debinarizer = new Debinarizer();

        for( int c = br.read(); c != -1; c = br.read() )
        {
            debinarizer.debinarize( Character.getNumericValue( (char)c ) );
        }

        br.close();
        bw.close();
    }
}
