import React, { Component } from 'react';
import { View, Text } from 'react-native';

// aqui puedes hacer tu zona, cada objeto es un punto
// const zone = [{long:0, lat:0},{long:0, lat:0}]
const zone =[
  [-100.31303416, 25.72532929],
  [-100.31273573, 25.7251696],
  [-100.31256565, 25.72515301],
  [-100.3126288, 25.7251713]
]

class GeolocationExample extends Component {
  constructor(props) {
    super(props);

    this.state = {
      latitude: null,
      longitude: null,
      poligon: null,
      inside:null,
      error: null,
      //aqui te creas un state para ver si esta adentro o no, un true o flase lo que quieras
    };
  }
  
  componentDidMount() {
    this.watchId = navigator.geolocation.watchPosition(
      (position) => {
        this.setState({
          latitude: position.coords.latitude,
          longitude: position.coords.longitude,
          inside:null,
          error: null,
          //aqui seteas el state que te dice si esta dentro o fuera obteniendo el valor de la funcion que hace la validacion
          //le estas pasando como parametro la zona que quieres checar y tu longitud y latitude
          //isInside: isInside(zone, position.coords.longitude, position.coords.latitude)
        });
      },
      (error) => this.setState({ error: error.message }),
      { enableHighAccuracy: true, timeout: 20000, maximumAge: 1000, distanceFilter: 10 },
    );
  }

  //Aqui declaras el metodo
  // a este metodo le puedes pasar 3 parametros, la zona que vas a verificar, y la long y lat del targe
  //isInside(zone,long,lat) {
  //Escribe aqui el algoritmo
  //}

  componentWillUnmount() {
    navigator.geolocation.clearWatch(this.watchId);
  }
  
  // aqui agrega un Text donde te imprima el state de isInside
  render() {
    return (
      <View style={{ flexGrow: 1, alignItems: 'center', justifyContent: 'center' }}>
        <Text>Latitude: {this.state.latitude}</Text>
        <Text>Longitude: {this.state.longitude}</Text>
        {this.state.error ? <Text>Error: {this.state.error}</Text> : null}
      </View>
    );
  }
}

export default GeolocationExample;
