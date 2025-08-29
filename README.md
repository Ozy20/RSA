# RSA GUI

A Java FX application that provides a graphical user interface for RSA encryption and decryption operations.

## Features

- User-friendly GUI built with Java FX
- RSA encryption and decryption functionality
- Cross-platform compatibility

## Requirements

- Java Runtime Environment (JRE) 8 or higher
- Windows operating system (for the provided batch file)

## How to Run

1. Navigate to the project directory:
   ```
   RSA\out\artifacts\RSA_jar\
   ```

2. Execute the batch file:
   ```
   ./run-rsa.bat
   ```

The application will launch and present the RSA GUI interface.

## Project Structure

```
RSA/
├── out/
│   └── artifacts/
│       └── RSA_jar/
│           ├── RSA.jar
│           └── run-rsa.bat
└── ...
```

## Usage

Once the application is running, you can use the GUI to:
- Auto-generation of keys to ensure a primality feature of some RSA params(p and q) as much as possible
- Encrypt text using RSA public keys
- Decrypt text using RSA private keys

## Support

For any issues or questions regarding the application, please refer to the source code or contact me.
