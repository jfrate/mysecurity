package mysecurity;

import java.util.Base64;

public class Base64Encoder {
    // Base64 character set
    private static final char[] BASE64_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".toCharArray();
    
    public static String encode(String input) {
        StringBuilder encoded = new StringBuilder();
        byte[] bytes = input.getBytes();  // Convert the string to a byte array
        int paddingCount = (3 - (bytes.length % 3)) % 3;  // Calculate the number of padding characters
    
        // Process each group of 3 bytes
        for (int i = 0; i < bytes.length; i += 3) {
            int b = ((bytes[i] & 0xFF) << 16) & 0xFFFFFF;  // First byte
            if (i + 1 < bytes.length) {
                b |= (bytes[i + 1] & 0xFF) << 8;  // Second byte
            }
            if (i + 2 < bytes.length) {
                b |= (bytes[i + 2] & 0xFF);  // Third byte
            }
    
            // Extract 6-bit chunks and map them to Base64 characters
            encoded.append(BASE64_CHARS[(b >> 18) & 0x3F]);
            encoded.append(BASE64_CHARS[(b >> 12) & 0x3F]);
            encoded.append(i + 1 < bytes.length ? BASE64_CHARS[(b >> 6) & 0x3F] : '=');
            encoded.append(i + 2 < bytes.length ? BASE64_CHARS[b & 0x3F] : '=');
        }
    
        return encoded.toString();
    }
    

        // Method to find the index of a character in the BASE64_CHARS array
        private static int getBase64CharIndex(char c) {
            for (int i = 0; i < BASE64_CHARS.length; i++) {
                if (BASE64_CHARS[i] == c) {
                    return i;
                }
            }
            return -1; // Shouldn't happen for valid Base64 characters
        }
    
        // Method to decode a Base64-encoded string
        public static String decode(String input) {
            // Remove padding characters if present
            int paddingCount = 0;
            if (input.endsWith("==")) {
                paddingCount = 2;
                input = input.substring(0, input.length() - 2);
            } else if (input.endsWith("=")) {
                paddingCount = 1;
                input = input.substring(0, input.length() - 1);
            }
    
            StringBuilder decoded = new StringBuilder();
            int group = 0;  // 24-bit group
    
            for (int i = 0; i < input.length(); i += 4) {
                group = 0;
                int charsToProcess = Math.min(4, input.length() - i);
    
                // Convert the next 4 Base64 characters into a 24-bit group
                for (int j = 0; j < charsToProcess; j++) {
                    group <<= 6;
                    group |= getBase64CharIndex(input.charAt(i + j));
                }
    
                // Shift to adjust for fewer characters in the last group (due to padding)
                group <<= 6 * (4 - charsToProcess);
    
                // Extract 8-bit bytes from the 24-bit group
                decoded.append((char) ((group >> 16) & 0xFF)); // First 8 bits
                if (charsToProcess > 2) {
                    decoded.append((char) ((group >> 8) & 0xFF));  // Second 8 bits
                }
                if (charsToProcess > 3) {
                    decoded.append((char) (group & 0xFF));  // Last 8 bits
                }
            }
    
            // Return the decoded string minus the padding bytes
            return decoded.substring(0, decoded.length() - paddingCount);
        }
    
    
    public static void main(String[] args) {
        //final Base64Encoder base64 = new Base64Encoder();
        // Original data to be encoded (string or binary data)
        String originalInput = "Hello, world!";
        
        // Encode the string into Base64        
        final String encodedString = Base64Encoder.encode(originalInput);
        final String decodedString = Base64Encoder.decode(encodedString);

        // Output the encoded result
        System.out.println("Original: " + originalInput);
        System.out.println("Base64 Encoded: " + encodedString);
        System.out.println("with lib:       " + Base64.getEncoder().encodeToString(originalInput.getBytes()));
        System.out.println("Base64 Decoded: " + decodedString);
    }
}