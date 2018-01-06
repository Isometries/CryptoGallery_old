# PhotoCrypt

PhotoCrypt is a simple way to store and view encrypted images in android. 

# Overview 

Images are copied over from the default android gallery, where they are then encrypted with a user-chosen symmetric key,  using java's default implementation of AES. The encrypted images are stored in internal storage, and thumbnail data is encrypted as well before being stored in the SQLIte database. 

# Usage Notes

The following are some important notes to keep in mind.

  - It is highly recommended to back-up any images before importing them to the app. This is not a back-up service. If the key is lost, or a bug occurs, you will lose all saved data. 

  - Currently the cipher uses 128 bit blocks, this will later be upgraded to AES 256, and a SHA-256 hash function to derive the key.
  
  - This has not been professionally audited, although measures have been made to guarantee the app's security, no assurances can be made.
  
  
# Planned
  
  
  Upgrading cipher to AES-256 at a later date. 
  UI imrpovments
  Bug fixes
  