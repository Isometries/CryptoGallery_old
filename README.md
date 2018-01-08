# PhotoCrypt

PhotoCrypt is a simple way to store and view encrypted images in android. 

# Overview 

Images are copied over from the default android gallery, where they are then encrypted with a user-chosen symmetric key,  using java's default implementation of AES. The encrypted images are stored in internal storage, and thumbnail data is encrypted as well before being stored in the SQLIte database. 

Before using, please keep in mind the following:

  - It is highly recommended to back-up any images before importing them to the app. If your key is lost, or a bug occurs, you will lose all saved data. 

  - Currently the app is based around the standard java librairy implemenation of AES-128 CBC. There are later plans to upgrade to AES 256.

# Usage Notes

  - Deleting photos
  
    - Multiple photos/albums may be deleted through a long press, followed by pressing the appropriate deletion button

  - Exports
  
    - Exporting photos or albums is done in a similar way to deletion; simply long press the desired item and press export
    
    - Exported items are located in a plaintext .zip folder inside the phones external download directory. 


# Planned

 - Exporting photos in an encrypted state, including a shell script to automatically handle decryption
 
 - Upgrading to AES 256
 
 - General UI improvements 
 
 - Bug fixes
 

