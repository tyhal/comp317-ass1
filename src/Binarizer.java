import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Binarizer
{
    private long phrase_count = 0;
    private static BufferedWriter bw;
    
    private String toBinary( long value, int num_bits )
    {
        String expression = "%" + Integer.toString( num_bits ) + "s";
        return String.format( expression, Long.toBinaryString( value ) ).replace( " ", "0" );
    }

    public void binarize( long phrase_num, char prefix, boolean has_mismatch ) throws IOException
    {
//         System.out.println( "> " + phrase_num + ", " + prefix + " | phrase_count: " + phrase_count );
     
        if( phrase_count != 0 ) // First symbol doesn't need phrase num encoded
        {
            // Calculate minimum number of bits needed to represent number
            int num_bits = Long.SIZE - Long.numberOfLeadingZeros( phrase_count );
//             System.out.print( toBinary( phrase_num, num_bits ) );
            bw.write( toBinary( phrase_num, num_bits ) );
        }
        
        if( has_mismatch )
        {
//             System.out.print( toBinary( prefix, 8 ) );
            bw.write( toBinary( prefix, 8 ) );
        }

        phrase_count = phrase_num == 0 ? 0 : phrase_count + 1; // 0 is the RESET phrase number
    }

    public static void main( String[] args ) throws IOException
    {
        final BufferedReader br = new BufferedReader( new InputStreamReader( System.in, "UTF-8" ) );
        bw = new BufferedWriter( new OutputStreamWriter( System.out, "UTF-8" ) );
        Binarizer binarizer = new Binarizer();

        for( String line = br.readLine(); line != null; line = br.readLine() )
        {
            String[] tokens = line.split( ",", 2 ); // Limit to 2 for case where value is ','
            long phrase_num = Long.parseLong( tokens[ 0 ] );
            char prefix = tokens.length == 2 ? (char)Integer.parseInt( tokens[ 1 ], 16 ) : '\0' /*can be anything*/;

            binarizer.binarize( phrase_num, prefix, tokens.length == 2 );
        }

        br.close();
        bw.close();
    }
}
