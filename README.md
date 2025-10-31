# bands-fm

BandsFM is an application that integrates and works as a wrapper for the following vercel app:

> https://bands-api.vercel.app/api

The main purpose of this application is to retrieve data from Bands and Albums and cache them, in a way that subsequent searches will be faster.

### running the application

To run the application you can use your IDE of choice or with the following command:

```sh
gradle clean build run
```

Or using the dockerfile with the following commands

```sh
docker build . -t bandsfm
docker run -p 8080:8080 --rm bandsfm
```
