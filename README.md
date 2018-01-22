# CryptoGallery

CryptoGallery is a simple way to store and view encrypted images in android.

# Overview

Images are copied over from the android file-system, where they are then encrypted with a user-chosen symmetric key, using java's default implementation of AES. The encrypted images are then stored in internal storage, and thumbnail data is encrypted before being stored in the SQLIte database.


# Usage Notes

Before using, please keep in mind the following:

  - It is highly recommended to back-up any images before importing them to the app. If your key is lost, or a bug occurs, you will lose all saved data.

  - Currently the app is based around the standard java library implemenation of AES-128 CBC. There are later plans to upgrade to AES 256.

  - Deleting photos

    - Multiple photos/albums may be deleted through a long press, followed by pressing the appropriate deletion button

  - Exports

    - Exporting photos or albums is done in a similar way to deletion; simply long press the desired item and press export

    - Exported items are located in a plaintext .zip folder inside the phones external download directory.


  - Performance

    - When dealing with a large number of entries, the UI can experience some lag. Working with albums of less than 150 photos is recommended.

    - After importing a large set of photos, the album should be refreshed, as it may take several seconds for the operation to finish.


# Building from source

  The source may be obtained either through `git clone https://gitlab.com/Isometricmap/PhotoCrypt.git`, or by downloading and unzipping the file.

  - Open the source with android studio, then click `Build` -> `Build APK(s)`.

# Dowloading the APK

  If building from source is not an option, you can use the APK [here https://gitlab.com/Isometricmap/CryptoGallery_APK ]. It includes both the signed APK, as well as the signing certificate fingerprints.

# Planned

 - Exporting photos in an encrypted state, including a shell script to automatically handle decryption

 - Upgrading encryption

   - An upgrade to AES 256

   - Allowing the user to export an encrypted zip in combination with a bash script to decrypt it.

 - General UI improvements

   - This includes a status bar for exporting/importing photos.

 - Bug fixes
