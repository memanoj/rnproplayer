import * as React from 'react';

import { StyleSheet, View, Text } from 'react-native';
import { multiply, AndroidPlayVideo } from 'rnproplayer';

export default function App() {
  const [result, setResult] = React.useState<number | undefined>();

  React.useEffect(() => {
    multiply(3, 8).then(setResult);
  }, []);

  const PlayVideo = () => {
    AndroidPlayVideo('https://www.learningcontainer.com/wp-content/uploads/2020/05/sample-mp4-file.mp4')
  }

  return (
    <View style={styles.container}>
      <Text onPress={PlayVideo} style={{color:'black'}}>Result: {result}</Text>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor:'white',
    alignItems: 'center',
    justifyContent: 'center',
  },
  box: {
    width: 60,
    height: 60,
    marginVertical: 20,
  },
});
