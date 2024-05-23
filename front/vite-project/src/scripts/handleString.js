export default {
  sliceText(text) {
    let convertText = text.substring(0, 20);
    if(text.length > 20) {
      convertText += '...';
    }

    return convertText;
  }
}