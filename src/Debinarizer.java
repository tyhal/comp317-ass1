import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Debinarizer
{
    private long phrase_count = 0;
    private long bits_to_decode = 0;
    private long bit_string = 0;
    private long bit_count = 0;
    private boolean decode_prefix = false;
    
    private String toBinary( long value, int num_bits )
    {
        String expression = "%" + Integer.toString( num_bits ) + "s";
        return String.format( expression, Long.toBinaryString( value ) ).replace( " ", "0" );
    }
    
    public void debinarize( int bit, boolean last )
    {   
        if( !decode_prefix && phrase_count == 0 ) // Cannot decode number of 0 bits so must add optimized out phrase #
        {
            System.out.print( 1 + "," );
            decode_prefix = true;
        }
        
        bits_to_decode = decode_prefix ? 8 : Long.SIZE - Long.numberOfLeadingZeros( phrase_count );
     
        bit_string ^= bit;
        bit_count++;
        
//         System.out.println( bit + ", " + bits_to_decode + ", " + toBinary( bit_string, 8 ) + " | phrase_count: " + phrase_count);

        if( bit_count == bits_to_decode )
        {
            if( decode_prefix )
            {
                System.out.println( Integer.toHexString( (int)bit_string ) );
                phrase_count++; // Phrase decoded
            }
            else
            {
                // -1 so that we add optimized out phrase number after the first phrase has been decoded since the reset
                phrase_count = bit_string == 0 ? -1 : phrase_count;
                System.out.print( bit_string + ( last ? "" : "," ) );
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
        Debinarizer debinarizer = new Debinarizer();
        
        for( int next_c, c = br.read(); c != -1; c = next_c )
        {
            final boolean last = ( ( next_c = br.read() ) == -1 );
            debinarizer.debinarize( Character.getNumericValue( (char)c ), last );
        }

        br.close();
    }
}