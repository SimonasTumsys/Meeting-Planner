import { useState } from "react";

function useTempState(defaultState) {
  const [state, setState] = useState(defaultState);

  const onStateChange = (e) => {
    setState(e.target.value);
  };

  const resetTempState = () => setState(null);

  return {
    state,
    onStateChange,
    resetTempState,
  };
}
export default useTempState;
