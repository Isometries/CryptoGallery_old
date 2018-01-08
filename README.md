# PhotoCrypt

PhotoCrypt is a simple way to store and view encrypted images in android. 

# Overview 

Images are copied over from the default android gallery, where they are then encrypted with a user-chosen symmetric key,  using java's default implementation of AES. The encrypted images are stored in internal storage, and thumbnail data is encrypted as well before being stored in the SQLIte database. 

# Usage Notes

The following are some important notes to keep in mind.

  - It is highly recommended to back-up any images before importing them to the app. This is not a back-up service. If the key is lost, or a bug occurs, you will lose all saved data. 

  - Currently the cipher uses 128 bit blocks, this will later be upgraded to AES 256, and a SHA-256 hash function to derive the key.
  
  - This has not been professionally audited Although measures have been made to guarantee the app's security, no assurances can be made.
  
  
# Planned
  
  Currently the only missing feature that is planned is an export option. The user will be given the option to export an album as either a plain-text, or encrypted zip file. 
  
  Users are encouraged to contribute code in the form of pull requests. This includes both security bugs, as well as improving the UI.
  