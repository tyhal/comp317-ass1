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

    /***
     * Builds up phrases or phrase numbers given bits.
     * 
     * @param bit           The bit to build a phrase or phrase number with.
     * @throws IOException
     */
    public void debinarize( int bit ) throws IOException
    {
        // If we want symbol then we need 8 bits, otherwise be need log2(phrase_count) bits
        bits_to_decode = decode_prefix ? 8 : Long.SIZE - Long.numberOfLeadingZeros( phrase_count );

        // build up phrase_number/phrase
        bit_string ^= bit;
        bit_count++;

        if( bit_count == bits_to_decode ) // Phrase/Phrase number built!
        {
            if( decode_prefix )
            {
                bw.write( "," + Integer.toHexString( (int)bit_string ) + "\n" );
                phrase_count++; // Symbol created so increment phrase count
            }
            else
            {
                bw.write( Long.toString( bit_string ) );

                if( bit_string == 0 ) // 0 is the RESET phrase number
                {
                    phrase_count = 0; // Reset phrase count such that we read in the correct number of bits.
                }
            }

            decode_prefix = !decode_prefix; // Alternate
            bit_count = 0;
            bit_string = 0; // Reset bit string
        }
        bit_string <<= 1;
    }

    public static void main( String[] args ) throws IOException
    {
        final BufferedReader br = new BufferedReader( new InputStreamReader( System.in, "UTF-8" ) );
        bw = new BufferedWriter( new OutputStreamWriter( System.out, "UTF-8" ) );
        Debinarizer debinarizer = new Debinarizer();

        char[] window = new char[8];
        char[] next_window = new char[8];

        for( int next_c, c = br.read(window); c != -1; c = next_c )
        {
            final boolean last = ( ( next_c = br.read(next_window) ) == -1 );

            if( !last )
            {
                // Debinarize every bit in 8-bit window
                for (int i = 0; i < 8; i++)
                {
                    debinarizer.debinarize( Character.getNumericValue( window[i] ) );
                }
            } else
            {
                int i = 7;
                // Padding will be 100... so iterate backwards until we hit 1
                for( ; i >= 0; i--)
                {
                    if( Character.getNumericValue( window[i] ) == 1 ) {
                        break;
                    }
                }
                // Debinarize remaining bits inside 8 bit window that aren't padding
                for(int j = 0; j < i; j++ )
                {
                    debinarizer.debinarize( Character.getNumericValue( window[j] ) );
                }
            }

            // Swap references to windows such that we don't have to copy but we alternate instead.
            char[] tmp_window = window;
            window = next_window;
            next_window = tmp_window;
        }

        br.close();
        bw.close();
    }
}
