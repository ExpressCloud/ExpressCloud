# ExpressCloud
A modular cloud system for Minecraft - easy and painless to set up

![GitHub issues](https://img.shields.io/github/issues/ExpressCloud/ExpressCloud)
![Discord](https://img.shields.io/discord/696497243661795339?label=Discord)

## Features

- Configurable loadbalancing
- Server categories
- Static servers
- Automatic restarting of servers
- Modular
- Extensive API
- much more...

This is not an all-in-one solution like some of the other cloud systems.
ExpressCloud does best at what it should do - manage your Minecraft servers.
Configure load balancing, use the API to retrieve cloud specific data or to run cloud specific operations.
ExpressCloud is designed to integrate in your already existing infrastructure - no fancy custom permission system, no lobby system, no chat system or any other things. Just a cloud system.

## How to install and run

Just download the newest version in the "Releases" section and start ExpressCloud as you would any other Java program. (`java -jar ExpressCloud.jar`)

Currently, only a Docker & Redis implementation combo exists, so you need those two installed and set up as well.
A standalone & Netty implementation combo is planned, so small installations can also run without dependencies.

Currently, ExpressCloud is not ready to be used in production, so there are no releases. (See section "How to build" if you want to try ExpressCloud out)

## How to build

ExpressCloud uses Maven for its build process, so you just have to run `mvn clean install` and after the build process is finished, there should be a Jar with all dependencies in Bootstrap target folder. [Currently no fat jar]

## Documentation

The documentation will be available in the GitHub wiki

## Getting help

If you need help or you have any other questions, please don't hesitate to join our [Discord Server](https://discord.gg/r4TqwC3)
