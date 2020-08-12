# Template App


## Introduction
ToDo

## Useful links & External services
ToDo

## Important information
ToDo

## Deployment & Publish instructions
Project has three different product flavors: `dev`,  `staging`,  `production`. Each flavor has different `appId` and points to different API endpoint.  App version name is pulled from the latest git tag. App version code is count of the git tags.

1. Clone this repository
2. Run `gradlew assembleProductionRelease` for apk or `gradlew bundleProductionRelease`for bundle in terminal
3. APK should be available in /build/outputs/apk_ directory
	Bundle should be available in /build/outputs/bundle_ directory

**Signing process:** Debug and release apps are signed with the same keystore. Keystore passwords are automatically loaded from property file during building the app.

## Code Style
We are keeping consistent code style. Use project codestyle.
We are also using ktLint and Detekt to keep our code clean. To easily format and check the formatting before creating new PR, you can run manually in Terminal this task: `./graldew ktlintFormat ktlintCheck detekt`. 
To make this easier, you can also create new "Run/Debug Configuration" -> Gradle -> Add New, and fill "Tasks:" with `ktlintFormat ktlintCheck detekt`.
You can also copy pre-push hook from _/extras/hooks/_ to _/.git/hooks/_. It will run all the checks before each push.

## Developed by
- [Lukas Hermann](mailto:lukas.hermann@strv.com)