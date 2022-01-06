# Install chromedriver

When installing chromedriver you must ensure you install a version which matches the version of Google Chrome on your machine. You can check what version of Google Chrome you have installed by using the following menu bar option.

Chrome > About Google Chrome

You can install chromedriver manually from its website or using Homebrew. 

## Homebrew install

Homebrew should automatically install the latest version of chromedriver, if your local Google Chrome install is up to date then this should all work fine.

`brew install chromedriver`

To check what version of chromedriver you have from homebrew you can use

`brew info chromedriver`

## Manual install 

https://chromedriver.chromium.org/

- Download the correct version of chromedriver for your local Google Chrome install
- Extract the binary file from the downloaded zip to a location which is on your `PATH` variable, e.g `/usr/local/bin`
 